create schema if not exists task_management_system;
set schema 'task_management_system';

create table workspaces
(
    workspace_id serial primary key,
    name         varchar(50) unique   not null
);

create table users
(
    user_id      serial primary key,
    name         varchar(25)          not null check (length(name) >= 8) unique,
    password     varchar(25)          not null check (length(password) >= 8),
    role         varchar(6)           not null check (role in ('admin', 'member')),
    workspace_id smallint             references workspaces (workspace_id) on delete set null
);