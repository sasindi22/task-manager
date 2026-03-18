import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { TaskService } from '../services/task.service';
import { Task } from '../models/task.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})

export class DashboardComponent implements OnInit {
  tasks: Task[] = [];
  today: Date = new Date();
  showModal: boolean = false;

  taskForm = new FormGroup({
    id: new FormControl<number | null>(null),
    title: new FormControl('', [Validators.required]),
    description: new FormControl(''),
    status: new FormControl('TO_DO'),
  });

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getTasks().subscribe({
      next: (data) => (this.tasks = data),
      error: (err) => console.error('Error fetching tasks', err),
    });
  }

  updateStatus(task: Task, newStatus: string) {
    if (!task.id) return;

    const oldStatus = task.status;
    task.status = newStatus;

    this.taskService.updateTask(task.id, task).subscribe({
      error: (err) => {
        console.error('Failed to update status on server', err);
        task.status = oldStatus;
      },
    });
  }

  getStatusColor(status: string | undefined): string {
    switch (status) {
      case 'TO_DO':
        return '#7ed957';
      case 'IN_PROGRESS':
        return '#ffde59';
      case 'DONE':
        return '#ff751f';
      default:
        return '#ccc';
    }
  }

  openModal() {
    this.taskForm.reset({ status: 'TO_DO' });
    this.showModal = true;
  }

  openEditModal(task: Task) {
    this.taskForm.patchValue(task);
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.taskForm.reset({ status: 'TO_DO' });
  }

  saveTask() {
    if (this.taskForm.valid) {
      const taskData = this.taskForm.value as Task;

      if (taskData.id) {
        this.taskService.updateTask(taskData.id, taskData).subscribe({
          next: () => {
            this.loadTasks();
            this.closeModal();
          },
          error: (err) => console.error('Error updating task', err),
        });
      } else {
        this.taskService.createTask(taskData).subscribe({
          next: () => {
            this.loadTasks();
            this.closeModal();
          },
          error: (err) => console.error('Error saving task', err),
        });
      }
    }
  }

  performSearch(keyword: string, status: string) {
    this.taskService.searchTasks(keyword, status).subscribe({
      next: (data) => {
        this.tasks = data;
      },
      error: (err) => {
        console.error('Search failed', err);
        this.loadTasks();
      },
    });
  }

  deleteTask(id: number) {
    if (confirm('Are you sure you want to delete this task?')) {
      this.taskService.deleteTask(id).subscribe({
        next: () => this.loadTasks(),
        error: (err) => console.error('Delete failed', err),
      });
    }
  }
}
