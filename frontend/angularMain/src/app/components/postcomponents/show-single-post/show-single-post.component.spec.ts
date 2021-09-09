import { Post } from '../../../models/post';
import { Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowSinglePostComponent } from './show-single-post.component';

describe('ShowSinglePostComponent', () => {
  let component: ShowSinglePostComponent;
  let fixture: ComponentFixture<ShowSinglePostComponent>;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowSinglePostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowSinglePostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
