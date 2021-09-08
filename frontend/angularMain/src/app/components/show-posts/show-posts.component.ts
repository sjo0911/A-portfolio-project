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
  $posts : Observable<Post[]>;
  pageIndex:number = 0;
  pageSize:number = 2;
  lowValue:number = 0;
  highValue:number = 2;

  constructor(
    private guestbookService : GuestbookService
  ) { }

  ngOnInit(): void {
    this.$posts = this.guestbookService.getByCategory(this.category) as Observable<Post[]>;
  }


  getPaginatorData(event : any){
    console.log(event);
    if(event.pageIndex === this.pageIndex + 1){
        this.lowValue = this.lowValue + this.pageSize;
        this.highValue =  this.highValue + this.pageSize;
      }
    else if(event.pageIndex === this.pageIndex - 1){
      this.lowValue = this.lowValue - this.pageSize;
      this.highValue =  this.highValue - this.pageSize;
      }
      this.pageIndex = event.pageIndex;
  }

}
