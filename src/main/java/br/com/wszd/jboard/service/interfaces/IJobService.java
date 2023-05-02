package br.com.wszd.jboard.service.interfaces;

import br.com.wszd.jboard.dto.JobDTO;
import br.com.wszd.jboard.model.Job;

import java.util.List;

public interface IJobService {

    public List<JobDTO> getAllJobs();

    public List<JobDTO> searchJobsByParam(String opportunity);

    public List<JobDTO> searchJobsByType(String type);

    public JobDTO getJobDTO(Long id);

    public Job getJob(Long id);

    public Job createNewJob(Job novo);

    public JobDTO editJob(Long id, Job novo);

    public Job saveEditJob(Job novo);

    public void deleteOneJob(Long id);
}
