import { User } from '../../../models/user';
import { Observable } from 'rxjs';
import { Post } from '../../../models/post';
import { AccountService } from '../../../services/account.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GuestbookService } from '../../../services/guestbook.service';
import { ipostInterface } from '../../../services/ipost-interface';
import { Component, Inject, InjectionToken, OnInit, Injectable, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-post-text',
  templateUrl: './post-text.component.html',
  styleUrls: ['./post-text.component.css']
})


export class PostTextComponent implements OnInit {
  @Input()
  category : string;
  @Output() postedText = new EventEmitter<boolean>();
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
      text: ['', Validators.required],
      name: ['']
    });
  }

  get postF() {return this.postTextForm.controls;}

  onSubmit() {
    this.postLoading = true;
    var newPost : Post = new Post();
    newPost.category = this.category;
    newPost.header = this.postF.header.value;
    newPost.text = this.postF.text.value;
    if(this.accountService.userValue != null)
    {
      newPost.name = this.accountService.userValue.firstName + " " + this.accountService.userValue.lastName;
    } else
    {
      if(this.postF.name.value === null){
        newPost.name = "anonymous"
      } else {
        newPost.name = this.postF.name.value;
      }
    }
    this.guestbookService.post(newPost).subscribe(
      () => {},
      () => {this.postLoading = false;},
      () => {
        this.postLoading = false;
        this.postedText.emit(true);
      }
      )
  }

}
