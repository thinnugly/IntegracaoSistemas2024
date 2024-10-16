import { Routes } from '@angular/router';
import { SigninComponent } from './auth/components/signin/signin.component';
import { SignupComponent } from './auth/components/signup/signup.component';

export const routes: Routes = [
    {
        path: '', redirectTo: 'auth/signin', pathMatch: 'full'
    },
    {
        path: 'auth/signin', component: SigninComponent,
    },
    {
        path: 'auth/signup', component: SignupComponent
    }
];
