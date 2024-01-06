package ma.dnaengineering.backend.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ma.dnaengineering.backend.dto.EmployeeUploadResponse;

public interface EmployeeService {

    public ResponseEntity<EmployeeUploadResponse> uploadAndProcessCsvFile(MultipartFile file);

}
