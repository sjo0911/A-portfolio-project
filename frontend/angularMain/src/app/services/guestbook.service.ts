import { Post } from './../models/post';
import { WebReqService } from './web-req.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GuestbookService {

  constructor(
    private webReqService : WebReqService
  ) { }

  post(post : Post) {
    return this.webReqService.post("api/posts", post);
  }

  getAll() {
    return this.webReqService.get("api/posts");
  }

  getById(id: string) {
    return this.webReqService.get(`api/posts/${id}`);
  }

  getByCategory(id: string) {
    return this.webReqService.get(`api/posts/${id}/category`);
  }

  update(id: string, post : Post) {
    return this.webReqService.put(`api/posts/${id}`, post)
  }

  delete(id: string){
    return this.webReqService.delete(`api/posts/${id}`)
  }
}
