import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TaskService } from 'src/app/services/task.service';
import { CreateTaskComponent } from '../create-task/create-task.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sky-background',
  templateUrl: './sky-background.component.html',
  styleUrls: ['./sky-background.component.css']
})
export class SkyBackgroundComponent implements OnInit {
      value!: number;
      tasks: any[]=[];

  constructor(private taskService:TaskService,public modalService: NgbModal,private router: Router) { }
  
  ngOnInit(): void {
    this.getTaskByUserId();
  }

  getTaskByUserId(){
      const userId =sessionStorage.getItem('userId');
      
      this.taskService.getTaskByUserId(userId).subscribe(
       response=>{
        this.tasks=response
        console.log(response);
       },
       error=>{
        if (error instanceof HttpErrorResponse) {
          console.error('HTTP Error:', error.message);
        } else {
          console.error('Other Error:', error);
        }
       }

      )
  }
  
  getStrings(){
    this.taskService.getString().subscribe(
      data => {
          console.log(data);
      },
      error => {
          console.error('Error fetching data:', error);
      }
  );
  }

  openModal() {
    console.log("opening modal")
    const modalRef = this.modalService.open(CreateTaskComponent);
    modalRef.result
    .then((result) => {
      this.createTask(result);
      console.log('Modal closed with:', result);
    })
    .catch((reason) => {
      if (reason !== 'Cross click') {
        console.warn('Modal dismissed with unexpected reason:', reason);
      }
    });
  
  } 

  createTask(createTask : any){
    if(createTask!=null){
        this.taskService.createTask(createTask).subscribe(data=>{
            console.log(data);
        },error=>{
          console.log('Error fetching data:', error)
        })
    }

  }

  updateStatus(taskId: number, newStatus: string) {
    this.taskService.changeStatus(taskId, { status: newStatus }).subscribe(response => {
        console.log('Status updated:', response.message);
    }, error => {
        console.error('Error updating status:', error);
    });
}

logout() {
  sessionStorage.removeItem('token');
  sessionStorage.removeItem('userId') 
  sessionStorage.removeItem('name');
  sessionStorage.removeItem('email') 
  this.router.navigate(['/login']); 
}

}
