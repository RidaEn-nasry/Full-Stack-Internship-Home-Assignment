
package ma.dnaengineering.backend.controllers;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ma.dnaengineering.backend.services.EmployeeService;
import ma.dnaengineering.backend.exceptions.CsvProcessingException;
import org.springframework.http.HttpStatus;
import ma.dnaengineering.backend.dto.EmployeeUploadResponse;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "${client.url}")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/upload")
    public ResponseEntity<EmployeeUploadResponse> uploadAndProcessCsvFile(@RequestParam("file") MultipartFile file)
            throws IOException {
        if (file.isEmpty()) {
            throw new CsvProcessingException("File is empty");
        }
        if (!file.getContentType().equals("text/csv")) {
            throw new CsvProcessingException("File is not csv");
        }

        return employeeService.uploadAndProcessCsvFile(file);
    }

    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<?> handleCsvProcessingException(CsvProcessingException ex) {
        // Log err for debugging purposes
        // Logger.error("Error occurred while processing csv file: " + ex.getMessage());
        JSONObject error = new JSONObject();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error.toString(), HttpStatus.BAD_REQUEST);
    }

}
