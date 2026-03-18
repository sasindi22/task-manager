export interface Task {
  id?: number;
  title: string;
  description: string;
  status: string; // TO_DO, IN_PROGRESS, DONE
  createdAt?: string;
}