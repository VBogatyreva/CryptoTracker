package ru.netology.cryptotracker.data.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}