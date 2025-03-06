import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
  signupForm : FormGroup;

  
  constructor(private loginService : LoginService,private fb : FormBuilder, private router: Router) { 
    this.signupForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email:['', Validators.required]
    })
      
  }
  ngOnInit(): void {
  }

  submit(){
    console.log("attacker")
    if(this.signupForm.valid){
      this.signupForm.value.role = "user"
      const formValues = this.signupForm.value;
      console.log( this.signupForm.value.role)
      this.loginService.signup(formValues).subscribe(
        data=>
        {
         console.log(data);
         console.log('Navigating to login...');
         this.router.navigate(['/login']).then(success => {
           if (success) {
             console.log('Navigation successful!');
           } else {
             console.log('Navigation failed!');
           }
         });
    
        },
        error=>
        {
        console.log(error);
        })
    }
  }

}
