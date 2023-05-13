package restaurant.domain.facadeImpl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.application.dto.jobDto.JobDTO;
import restaurant.application.dto.jobDto.JobWithIdDTO;
import restaurant.domain.facade.CRUDFacade;
import restaurant.domain.model.IDObject;
import restaurant.domain.model.Job;
import restaurant.infrastructure.repository.JobRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JobFacadeImpl implements CRUDFacade<JobDTO, JobWithIdDTO> {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    public Job toJobFromDTO(JobDTO jobDTO) {
        return modelMapper.map(jobDTO, Job.class);
    }
    public JobDTO toJobDTO(Job job) {
        return modelMapper.map(job, JobDTO.class);
    }

    public JobWithIdDTO toJobWithIdDTO(Job job) {
        return modelMapper.map(job, JobWithIdDTO.class);
    }

    @Override
    public Optional<JobDTO> get(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return Optional.ofNullable(toJobDTO(job));
    }

    @Override
    public void delete(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(JobDTO jobDTO, Long id) {
        if (jobRepository.existsById(id)) {
            Job updatedJob = toJobFromDTO(jobDTO);
            updatedJob.setId(id);
            if(!jobRepository.existsByName(updatedJob.getName()) || jobRepository.findByName(updatedJob.getName()).get().getId().equals(id)) {
                jobRepository.save(updatedJob);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job with this name already exists in database!");
            }
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with this ID doesn't exist in database");
        }
    }

    @Override
    public IDObject add(JobDTO jobDTO) {
        Job job = toJobFromDTO(jobDTO);
        if(!jobRepository.existsByName(job.getName())) {
            jobRepository.save(job);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job with this name already exists in database!");
        }
        return new IDObject(job.getId());
    }

    @Override
    public List<JobWithIdDTO> getAll() {
        List<JobWithIdDTO> jobs = new ArrayList<>();
        for (Job job : jobRepository.findAll()) {
            jobs.add(toJobWithIdDTO(job));
        }
        return jobs;
    }
}
