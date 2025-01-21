package ru.otus.hw.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book") // Название объекта-сущности в java
@Table(name = "books") // Название таблицы в БД
public class Book {

    /*
    GenerationType.AUTO — выбор генератора осуществляется на основе диалекта.
        Не самый лучший вариант, так как тут как раз действует правило “явное лучше неявного”.
    GenerationType.IDENTITY — самый простой способ конфигурирования генератора.
        Он опирается на auto-increment колонку в таблице.
        Следовательно, чтобы получить id при persist-е нам нужно сделать insert.
        Именно поэтому он исключает возможность отложенного persist-а и следовательно batching-а.
    GenerationType.SEQUENCE — наиболее удобный случай, когда id мы получаем из sequence.
    GenerationType.TABLE — в этом случае hibernate эмулирует sequence через дополнительную таблицу.
        Не самый лучший вариант, т.к. в таком решении hibernate приходится юзать отдельную транзакцию и lock на строчку.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = true, unique = false)
    private String title;

    /*
    У книги, в нашем случае, может быть только один автор,
        а вот у автора может быть много книг
     */
    /*
    CascadeType - что будет происходить с зависимыми сущностями, если мы меняем главную сущность
    _
    CascadeType.ALL - все действия, которые мы выполняем с родительским объектом,
        нужно повторить и для его зависимых объектов.
    CascadeType.PERSIST - для новых сущностей вызывается persist(),
        а для уже существующих сущностей (у которых id != null) вызывается метод merge().
        - если мы сохраняем в базу родительский объект, то это же нужно сделать и с его зависимыми объектами.
    CascadeType.MERGE - если мы обновляем в базе родительский объект,
        то это же нужно сделать и с его зависимыми объектами.
    CascadeType.REMOVE / CascadeType.DELETE - если мы удаляем в базе родительский объект,
        то это же нужно сделать и с его зависимыми объектами.
    CascadeType.REFRESH / CascadeType.SAVE_UPDATE - дублируют действия, которые выполняются с родительским
        объектом к его зависимому объекту.
    CascadeType.DETACH - если мы удаляем родительский объект из сессии, то это же нужно сделать и
        с его зависимыми объектами.
     */
    /*
    Orphan
    Он используется для того, чтобы не оставалось дочерних сущностей без родительских.
    Если этот параметр выставлен в true, то дочерняя сущность будет удалена, если на нее исчезли все ссылки.
        Это не совсем то же самое, что и Cascade.REMOVE.
     */
    /*
    Fetch - Параметр fetch позволяет управлять режимами загрузки зависимых объектов.
    FetchType.LAZY - при загрузке родительской сущности, дочерняя сущность загружена не будет.
        Вместо нее будет создан proxy-объект.
        С помощью этого proxy-объекта Hibernate будет отслеживать обращение к этой дочерней сущности и
        при первом обращении загрузит ее в память.
    FetchType.EAGER - при загрузке родительской сущности будут загружены и все ее дочерние сущности. Кроме того,
        Hibernate постарается сделать это одним SQL-запросом, сгенерировав здоровенный запрос
        и сразу получив все данные.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinColumn(name = "author_id", nullable = true, unique = false)
    private Author author;

    /*
    У книги, в нашем случае, может быть только один жанр,
        а у жанра может быть много книг
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Genre.class)
    @JoinColumn(name = "genre_id", nullable = true, unique = false)
    private Genre genre;

}