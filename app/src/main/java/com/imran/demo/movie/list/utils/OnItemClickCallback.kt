package com.imran.demo.movie.list.utils

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */

interface OnItemClickCallback<Model>{
    fun onClick(model: Model)
}