import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../shared/material.module';
import { noSpecialCharsAndMinLength } from '../../../validators/no-numbers.validator'

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule, CommonModule, MaterialModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  router = inject(Router)
  signUp: FormGroup;

  constructor(){
    this.signUp = new FormGroup({
      username: new FormControl('', [noSpecialCharsAndMinLength()]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    });
  }

  onSubmitSignUp(){
    if(this.signUp.valid){
      if(this.signUp.valid){

        const newUser: User = {
          username: this.signUp.value.username ?? '',
          email: this.signUp.value.email ?? '',
          password: this.signUp.value.password ?? '',
        };
        
        const isLocalData = localStorage.getItem('angular18');
        let localArray = isLocalData ? JSON.parse(isLocalData) : [];
        
        localArray.push(newUser);
        localStorage.setItem('angular18', JSON.stringify(localArray));
    
        alert('Usuário '+this.signUp.value.email+' cadastrado com sucesso.');
        this.router.navigateByUrl('auth/signin');

      }
      
    }
  }

  signInView(){
    this.router.navigateByUrl('auth/signin');
  }
}

export interface User {
  username: string;
  email: string;
  password: string;
}
