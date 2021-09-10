import { User } from './../../../models/user';
import { first } from 'rxjs/operators';
import { AccountService } from './../../../services/account.service';
import { Router } from '@angular/router';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  errors : string = '';
  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private accountService : AccountService,
  ) {

      if (this.accountService.userValue) {
          this.router.navigate(['/']);
      }
  }

  ngOnInit() {
      this.registerForm = this.formBuilder.group({
        email: ['', Validators.compose([Validators.required, Validators.email])],
          firstName: ['', Validators.required],
          lastName: ['', Validators.required],
          username: ['', Validators.required],
          password: ['', [Validators.required, Validators.minLength(6)]]
      });
  }

  get f() { return this.registerForm.controls; }

  onSubmit() {
      this.submitted = true;

      if (this.registerForm.invalid) {
          return;
      }

      this.loading = true;

      var newUser : User = {
        email: this.f.email.value,
        firstName: this.f.firstname.value,
        lastName: this.f.lastname.value,
        username: this.f.username.value,
        scopes: new Array(),
        loginDates: new Array()
      } as User;
      newUser.email = this.f.email.value;
      newUser.firstName = this.f.firstname.value;
      newUser

      this.accountService.register(newUser)
      .pipe(first())
      .subscribe({
        next: () => {
          this.errors = "";
        },
        error: error => {
          this.loading = false;
          this.errors = error.error.errors[0];
        },
        complete: () => {
          this.loading = false
          this.errors = "";
          this.router.navigate(['', { outlets: { userSidebar: [ 'UserPage'] }}]);
        }
      })
  }
}
