import { Post } from './../../models/post';
import { Observable } from 'rxjs';
import { GuestbookService } from './../../services/guestbook.service';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-show-posts',
  templateUrl: './show-posts.component.html',
  styleUrls: ['./show-posts.component.css']
})
export class ShowPostsComponent implements OnInit {
  @Input()
  category : string;
  $posts : Observable<Post>;
  constructor(
    private guestbookService : GuestbookService
  ) { }

  ngOnInit(): void {

  }

}
