import { Post } from './../../models/post';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-show-single-post',
  templateUrl: './show-single-post.component.html',
  styleUrls: ['./show-single-post.component.css']
})
export class ShowSinglePostComponent implements OnInit {
  @Input()
  post : Post;

  constructor() {
    console.log(this.post)
   }


  ngOnInit(): void {
  }

}
