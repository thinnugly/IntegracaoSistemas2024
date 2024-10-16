import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MaterialModule } from '../../../shared/material.module';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [RouterOutlet, MaterialModule, ReactiveFormsModule, CommonModule],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css'
})
export class SigninComponent {

  router = inject(Router)
  signIn: FormGroup;

  constructor(){
    this.signIn = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    });
  }

  onSubmitSignIn(){
    if(this.signIn.valid){
      console.log('SIGNIN Form Field Values: ', this.signIn.value);
    }
  }

  signUpView(){
    this.router.navigateByUrl('auth/signup')
  }
}
