package com.example.model

import org.joda.time.DateTime

case class Post(
  id: Long,
  title: String,
  content: String,
  postedAt: DateTime)
