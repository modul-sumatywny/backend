package restaurant.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import restaurant.application.dto.jobDto.JobDTO;
import restaurant.application.dto.jobDto.JobWithIdDTO;
import restaurant.domain.facadeImpl.JobFacadeImpl;
import restaurant.domain.model.IDObject;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JobController {
    private final JobFacadeImpl jobFacadeImpl;

    @Autowired
    public JobController(JobFacadeImpl jobFacadeImpl) { this.jobFacadeImpl = jobFacadeImpl; }

    @GetMapping("/{jobId}")
    public Optional<JobDTO> getJob(@PathVariable Long jobId) {
        return jobFacadeImpl.get(jobId);
    }

    @PostMapping("/add")
    public IDObject addJob(@Valid @RequestBody JobDTO jobDTO) {
        return jobFacadeImpl.add(jobDTO);
    }

    @DeleteMapping("/delete/{jobId}")
    public void deleteJob(@PathVariable Long jobId) { jobFacadeImpl.delete(jobId); }

    @PutMapping("/update/{jobId}")
    public void updateJob(@Valid @RequestBody JobDTO jobDTO, @PathVariable Long jobId) {
        jobFacadeImpl.update(jobDTO, jobId);
    }

    @GetMapping("/allJobs")
    public List<JobWithIdDTO> getAllJobs() {
        return jobFacadeImpl.getAll();
    }
}
