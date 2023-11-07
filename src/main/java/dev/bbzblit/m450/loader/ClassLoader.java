package dev.bbzblit.m450.loader;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

@Service
public class ClassLoader {

    private final StundenplanLoader stundenplanLoader;

    private final ClassRepository classRepository;

    public ClassLoader(StundenplanLoader stundenplanLoader, ClassRepository classRepository) {
        this.stundenplanLoader = stundenplanLoader;
        this.classRepository = classRepository;
    }

    public void load(){
        Scanner file =  new Scanner("/classes.txt");
        while (file.hasNext()){
            String className = file.nextLine();
            String classId = file.nextLine();

            this.classRepository.findById(Long.parseLong(classId)).orElseGet(() -> {
                SchoolClass newSchoolClass = new SchoolClass();
                newSchoolClass.setName(className);
                return this.classRepository.save(newSchoolClass);
            });

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.bbzbl.ch/helper/export_stundenplan.php?klasse=3494261"))
                    .method("GET", null)
                    .build();
            try {
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                String content = response.body();
                this.stundenplanLoader.load(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
