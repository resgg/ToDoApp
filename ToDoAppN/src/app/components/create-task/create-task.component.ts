import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent implements OnInit {

  @Input() public user: any;
  @Output() passEntry: EventEmitter<any> = new EventEmitter();

  task = {
    assignto: '',
    taskname: '',
    status: '',
    userid: '',
  };

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() { }

  passBack(taskForm: any) {
    if (taskForm.valid) {
      const userId = sessionStorage.getItem('userId') ?? '';
      this.task.userid = userId;
      this.passEntry.emit(this.task);
      this.activeModal.close(this.task);
    } else {
      alert('Please fill in all required fields.');
    }
  }
  
}
