import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  loginForm : FormGroup;

  constructor(private loginService : LoginService,private fb : FormBuilder, private router: Router) { 
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
      
  }

  ngOnInit(): void {
   this.getStrings();
  }


  getStrings(){
    this.loginService.getString().subscribe(
      data => {
          console.log(data);
      },
      error => {
          console.error('Error fetching data:', error);
      }
  );
  }


  submit(){
    console.log("attacker")
    if(this.loginForm.valid){
      const formValues = this.loginForm.value;
      this.loginService.login(formValues).subscribe(
        data=>
        {
         sessionStorage.setItem('token', JSON.stringify(data.token));
         sessionStorage.setItem('userId',(data.userId)) 
         sessionStorage.setItem('name', JSON.stringify(data.name));
         sessionStorage.setItem('email', JSON.stringify(data.email)) 
         console.log(data);
         console.log('Navigating to dashboard...');
         this.router.navigate(['/dashboard']).then(success => {
           if (success) {
             console.log('Navigation successful!');
           } else {
             console.log('Navigation failed!');
           }
         });
         console.log("why")
        },
        error=>
        {
        console.log(error);
        })
    }
  }



}
