import { Observable } from 'rxjs';
import { Post } from './../../models/post';
import { AccountService } from './../../services/account.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GuestbookService } from './../../services/guestbook.service';
import { ipostInterface } from '../../services/ipost-interface';
import { Component, Inject, InjectionToken, OnInit, Injectable, Input } from '@angular/core';

@Component({
  selector: 'app-post-text',
  templateUrl: './post-text.component.html',
  styleUrls: ['./post-text.component.css']
})


export class PostTextComponent implements OnInit {
  @Input()
  category : string;
  postTextForm: FormGroup;
  postLoading = false;
  postSubmitted = false;
  constructor(
      private guestbookService : GuestbookService,
      private formBuilder: FormBuilder,
      private accountService: AccountService
    ) {
   }

  ngOnInit(): void {
    this.postTextForm = this.formBuilder.group({
      header: ['', Validators.required],
      text: ['', Validators.required]
    });
  }

  get postF() {return this.postTextForm.controls;}

  onSubmit() {
    this.postLoading = true;
    var newPost : Post = new Post();
    newPost.category = this.category;
    newPost.header = this.postF.header.value;
    newPost.text = this.postF.text.value;
    if(this.category == "main" && this.accountService.userValue != null)
    {
      newPost.name = this.accountService.userValue.firstName + " " + this.accountService.userValue.lastName;
    } else {
      newPost.name = "Anonymous";
    }
    this.guestbookService.post(newPost).subscribe(
      () => {},
      () => {this.postLoading = false;},
      () => {this.postLoading = false;}
      )
  }

}
