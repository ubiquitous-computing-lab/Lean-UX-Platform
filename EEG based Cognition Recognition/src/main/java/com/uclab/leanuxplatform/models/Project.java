/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uclab.leanuxplatform.models.constants.ModalityEnum;
import com.uclab.leanuxplatform.models.constants.TaskType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private int id;
    private int number_participants;
    private String title;
    private String description;
    private User created_by;
    private CopyOnWriteArrayList<Modality> modalities;
    private CopyOnWriteArrayList<Task> tasks;
    ;
    private CopyOnWriteArrayList<Participant> participants;
    ;
    private Iterator<Task> taskIterator = null;

    Project(int projectId) {
        this();
        this.id = projectId;
    }

    Project() {
        this.participants = new CopyOnWriteArrayList(new ArrayList());
        this.tasks = new CopyOnWriteArrayList(new ArrayList());
        this.modalities = new CopyOnWriteArrayList(new ArrayList());
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", title=" + title + ", description=" + description + ", user=" + created_by + ", modalities=" + modalities + ", tasks=" + tasks + ", participants=" + participants + '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreated_by() {
        return created_by;
    }

    public void setCreated_by(User created_by) {
        this.created_by = created_by;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants.addAll(participants);
    }

    public Participant getParticipantById(int activeParticipantId) {
        for (int i = 0; i < this.participants.size(); i++) {
            if (this.participants.get(i).getId() == activeParticipantId) {
                return this.participants.get(i);
            }
        }
        System.out.println("Selected participant(" + activeParticipantId + ") not found");
        return null;
    }

    public List<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(List<Modality> modalities) {
        this.modalities.addAll(modalities);
    }

    public boolean hasModality(ModalityEnum modalityEnum) {
        for (int i = 0; i < this.modalities.size(); i++) {
            if (((Modality) this.modalities.get(i)).getName().equals(modalityEnum.name())) {
                return true;
            }
        }
        return false;
    }

    public int getNumber_participants() {
        return number_participants;
    }

    public void setNumber_participants(int number_participants) {
        this.number_participants = number_participants;
    }

    public boolean hasTask(TaskType task_type) {
        for (int i = 0; i < this.tasks.size(); i++) {
            if (((Task) this.tasks.get(i)).getType().equals(task_type)) {
                return true;
            }
        }
        return false;
    }

    public Task getNextDuringTask() {
        if (taskIterator == null) {
            taskIterator = tasks.iterator();
        }
        Task nextTask = null;
        if (taskIterator.hasNext()) {
            nextTask = taskIterator.next();
            if (nextTask.getType().equals(TaskType.DURING)) {
                return nextTask;
            } else {
                nextTask = null;
                while (taskIterator.hasNext()) {
                    nextTask = taskIterator.next();
                    if (nextTask.getType().equals(TaskType.DURING)) {
                        break;
                    } else {
                        nextTask = null;
                    }
                }
            }
        }
        return nextTask;
    }

    public void initTaskIterator() {
        taskIterator = tasks.iterator();
    }

    public Task getTask(TaskType taskType) {
        for (int i = 0; i < this.tasks.size(); i++) {
            if (((Task) this.tasks.get(i)).getType().equals(taskType)) {
                return this.tasks.get(i);
            }
        }
        return null;
    }
}
