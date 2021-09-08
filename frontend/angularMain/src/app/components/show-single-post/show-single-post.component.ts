import { GuestbookService } from './../../services/guestbook.service';
import { AccountService } from './../../services/account.service';
import { Post } from './../../models/post';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-show-single-post',
  templateUrl: './show-single-post.component.html',
  styleUrls: ['./show-single-post.component.css']
})
export class ShowSinglePostComponent implements OnInit {
  @Input()
  post : Post;
  @Output()
  newItemEvent = new EventEmitter<string>();

  constructor(private accountService : AccountService, private guestbookService: GuestbookService) {
   }


  ngOnInit(): void {
  }

  refreshParent() {
    this.newItemEvent.emit("timeToRefresh!");
  }

  deletePost() {
    this.guestbookService.delete(this.post.id).subscribe(
      () => {},
      () => {},
      () => {
        this.refreshParent()
      })
  }

  checkIfAdmin(): boolean {
    if(!this.accountService.userValue || !this.accountService.userValue.scopes){
      return false
    } else if(this.accountService.userValue.scopes.some(scope => scope == "admin")) {
      return true;
    } else {
      return false;
    }
  }

}
