package net.javaguides.springboot.controller;

import net.javaguides.springboot.response.UploadPhotoResponse;
import net.javaguides.springboot.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/upload")
public class UploadController extends BaseController<UploadPhotoResponse, String> {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service-key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String supabaseBucket;

    protected UploadController() {
        super(UploadPhotoResponse.class);
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        // Kiểm tra file trống
        if (file.isEmpty()) {
            return handleSuccess(null, "File rỗng", Constants.FAILURE);
        }

        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody fileBody = RequestBody.create(file.getBytes(),
                    MediaType.parse(Objects.requireNonNull(file.getContentType())));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Request request = new Request.Builder()
                    .url(supabaseUrl + "/storage/v1/object/" + supabaseBucket + "/" + fileName)
                    .post(fileBody)
                    .addHeader("Authorization", "Bearer " + supabaseKey)
                    .addHeader("apikey", supabaseKey)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String publicUrl = supabaseUrl + "/storage/v1/object/public/" + supabaseBucket + "/" + fileName;
                return handleSuccess(publicUrl, "Upload thành công", Constants.SUCCESS);
            } else {
                assert response.body() != null;
                return handleSuccess(response.body().string(), response.message(), response.code());
            }
        } catch (HttpClientErrorException e) {
            return handleException(e);
        } catch (IOException e) {
            UploadPhotoResponse response = new UploadPhotoResponse();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(Constants.FAILURE);
            return ResponseEntity.ok(response);
        }
    }
}
