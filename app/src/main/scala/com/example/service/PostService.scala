package com.example.service

import com.example.dao.PostTable
import com.example.model.Post

class PostService(dao: PostTable) {
  def all(): Seq[Post] = dao.all()
  def create(title: String, content: String): Post = dao.insert(title, content)
}
