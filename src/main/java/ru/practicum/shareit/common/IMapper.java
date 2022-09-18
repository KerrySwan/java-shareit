package ru.practicum.shareit.common;

public interface IMapper<M extends Model, D extends Dto> {

    D toDto(M m);

    M toModel(D d);

}
