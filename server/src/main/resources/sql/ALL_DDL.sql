create table iosdb.color
(
    color_id int auto_increment
        primary key,
    color    varchar(255) null
);

create table iosdb.manufacturer
(
    manufacturer_id int auto_increment
        primary key,
    manufacturer    varchar(255) null
);

create table iosdb.material_type
(
    material_type_id int auto_increment
        primary key,
    type             varchar(255) null
);

create table iosdb.material_usage_audit
(
    material_usage_audit_id int auto_increment
        primary key,
    date_performed          datetime(6) not null,
    delta_amount            double      null,
    color_id                int         null,
    manufacturer_id         int         null,
    material_type_id        int         null,
    constraint FKc8rxi6n0aj2k5w9gqf3fg5amn
        foreign key (material_type_id) references iosdb.material_type (material_type_id),
    constraint FKf0yl8rf8g82fmcbp2cb3oy5df
        foreign key (color_id) references iosdb.color (color_id),
    constraint FKtmywntr5n8485p46qly808gyf
        foreign key (manufacturer_id) references iosdb.manufacturer (manufacturer_id)
);

create table iosdb.product
(
    product_id           int auto_increment
        primary key,
    name                 varchar(255) null,
    prod_mat_expenditure double       null,
    size                 double       null,
    sku                  varchar(255) null,
    material_type_id     int          null,
    constraint FKjbbl95pq2irf05m2wpc4hnki5
        foreign key (material_type_id) references iosdb.material_type (material_type_id)
);

create table iosdb.product_accessory
(
    product_accessory_id int auto_increment
        primary key,
    name                 varchar(255) null,
    prod_mat_expenditure double       null,
    size                 double       null,
    material_type_id     int          null,
    product_id           int          null,
    constraint FKaw5d8v7kj5p7v8o91yihr1l0l
        foreign key (product_id) references iosdb.product (product_id),
    constraint FKb25j5do070p40mklaxhlr3ix3
        foreign key (material_type_id) references iosdb.material_type (material_type_id)
);

create table iosdb.production_material_audit
(
    production_material_id int auto_increment
        primary key,
    amount                 double       null,
    date_inserted          datetime(6)  not null,
    name                   varchar(255) null,
    color_id               int          null,
    manufacturer_id        int          null,
    material_type_id       int          null,
    constraint FK8bwy2ohltw4ianwquymvxhbkn
        foreign key (material_type_id) references iosdb.material_type (material_type_id),
    constraint FKaregd9coxrsgaigovrfx6pe9n
        foreign key (manufacturer_id) references iosdb.manufacturer (manufacturer_id),
    constraint FKcpy5dnw493nr3n3pnwqn3d7lq
        foreign key (color_id) references iosdb.color (color_id)
);

create definer = root@localhost trigger iosdb.add_calculate_material_amount
    after insert
    on iosdb.production_material_audit
    for each row
BEGIN

    IF EXISTS
        (SELECT * FROM production_material_calculated
                WHERE new.manufacturer_id = production_material_calculated.manufacturer_id
                AND new.color_id = production_material_calculated.color_id
                AND new.material_type_id = production_material_calculated.material_type_id
                AND new.name = production_material_calculated.name )
    THEN
        UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = new.amount + production_material_calculated.amount_calculated
        WHERE new.manufacturer_id = production_material_calculated.manufacturer_id
        AND new.color_id = production_material_calculated.color_id
        AND new.material_type_id = production_material_calculated.material_type_id
        AND new.name = production_material_calculated.name;
    ELSE
        INSERT INTO production_material_calculated VALUES(null, new.amount, new.name, new.color_id, new.manufacturer_id, new.material_type_id);
    END IF;

END;

create definer = root@localhost trigger iosdb.delete_calculate_material_amount
    after delete
    on iosdb.production_material_audit
    for each row
BEGIN

    UPDATE production_material_calculated
    SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - old.amount
    WHERE old.manufacturer_id = production_material_calculated.manufacturer_id
    AND old.color_id = production_material_calculated.color_id
    AND old.material_type_id = production_material_calculated.material_type_id
    AND old.name = production_material_calculated.name;

END;

create definer = root@localhost trigger iosdb.update_calculate_material_amount
    after update
    on iosdb.production_material_audit
    for each row
BEGIN
    IF(new.color_id != old.color_id
        OR new.material_type_id != old.material_type_id
        OR new.manufacturer_id != old.manufacturer_id
        OR new.name != old.name)
    THEN
        UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - old.amount
        WHERE old.manufacturer_id = production_material_calculated.manufacturer_id
        AND old.color_id = production_material_calculated.color_id
        AND old.material_type_id = production_material_calculated.material_type_id
        AND old.name = production_material_calculated.name;

        IF(EXISTS(SELECT * FROM production_material_calculated
                WHERE new.manufacturer_id = production_material_calculated.manufacturer_id
                AND new.color_id = production_material_calculated.color_id
                AND new.material_type_id = production_material_calculated.material_type_id
                AND new.name = production_material_calculated.name))
        THEN
            UPDATE production_material_calculated
            SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated + new.amount
            WHERE new.manufacturer_id = production_material_calculated.manufacturer_id
            AND new.color_id = production_material_calculated.color_id
            AND new.material_type_id = production_material_calculated.material_type_id
            AND new.name = production_material_calculated.name;
        ELSE
            INSERT INTO production_material_calculated VALUES(null, new.amount, new.name, new.color_id, new.manufacturer_id, new.material_type_id);
        END IF;
    ELSE
        IF(new.amount > old.amount) THEN
            UPDATE production_material_calculated
            SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated + (new.amount - old.amount)
            WHERE new.manufacturer_id = production_material_calculated.manufacturer_id
            AND new.color_id = production_material_calculated.color_id
            AND new.material_type_id = production_material_calculated.material_type_id
            AND new.name = production_material_calculated.name;
        ELSEIF(new.amount < old.amount) THEN
            UPDATE production_material_calculated
            SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - (old.amount - new.amount)
            WHERE new.manufacturer_id = production_material_calculated.manufacturer_id
            AND new.color_id = production_material_calculated.color_id
            AND new.material_type_id = production_material_calculated.material_type_id
            AND new.name = production_material_calculated.name;
        END IF;
    END IF;
END;

create table iosdb.production_material_calculated
(
    p_m_calc_id       int auto_increment
        primary key,
    amount_calculated double       null,
    name              varchar(255) null,
    color_id          int          null,
    manufacturer_id   int          null,
    material_type_id  int          null,
    constraint FK2s8e69n4t0f0tm90lv9r4i00t
        foreign key (manufacturer_id) references iosdb.manufacturer (manufacturer_id),
    constraint FKawhva05rvpxdwtney6309sudt
        foreign key (material_type_id) references iosdb.material_type (material_type_id),
    constraint FKm2vkj61uhbogptdmtqinchvsg
        foreign key (color_id) references iosdb.color (color_id)
);

create table iosdb.product_order
(
    product_order_id int auto_increment
        primary key,
    comments         varchar(255)         null,
    completed        tinyint(1) default 0 not null,
    date_completed   datetime(6)          null,
    date_created     datetime(6)          not null,
    quantity         int                  not null,
    color_id         int                  null,
    product_id       int                  not null,
    p_m_calc_id      int                  null,
    constraint FK4lv44ghfoglw2kmncu010o4nc
        foreign key (color_id) references iosdb.color (color_id),
    constraint FKg1a8ubb85qtukpnhp2wllhgrl
        foreign key (p_m_calc_id) references iosdb.production_material_calculated (p_m_calc_id),
    constraint FKh73acsd9s5wp6l0e55td6jr1m
        foreign key (product_id) references iosdb.product (product_id)
);

create table iosdb.product_accessory_order
(
    product_accessory_order_id int auto_increment
        primary key,
    completed                  tinyint(1) default 0 not null,
    date_completed             datetime(6)          null,
    date_created               datetime(6)          not null,
    quantity                   int                  null,
    color_id                   int                  null,
    product_accessory_id       int                  not null,
    product_order_id           int                  not null,
    p_m_calc_id                int                  null,
    constraint FKddm61ashoa0fw74yl5r47f8x5
        foreign key (p_m_calc_id) references iosdb.production_material_calculated (p_m_calc_id),
    constraint FKgn2jirj508ii4ax5o736dave5
        foreign key (product_accessory_id) references iosdb.product_accessory (product_accessory_id),
    constraint FKjxjreouu2vvlftknthq1yq41i
        foreign key (color_id) references iosdb.color (color_id),
    constraint FKt5rc2dep7uner507dhahqjqkm
        foreign key (product_order_id) references iosdb.product_order (product_order_id)
);

create definer = root@localhost trigger iosdb.deduct_material_after_complete_a_o
    after update
    on iosdb.product_accessory_order
    for each row
BEGIN

    SELECT product_accessory.prod_mat_expenditure
    INTO @mat_expended
    FROM product_accessory
    WHERE product_accessory.product_accessory_id = new.product_accessory_id;

    IF(old.completed = false && new.completed = true) THEN
        UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - (@mat_expended * new.quantity)
        WHERE new.p_m_calc_id = production_material_calculated.p_m_calc_id;
    ELSEIF(old.completed = true && new.completed = false) THEN
                UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated + (@mat_expended * new.quantity)
        WHERE new.p_m_calc_id = production_material_calculated.p_m_calc_id;
    END IF;

END;

create definer = root@localhost trigger iosdb.deduct_material_after_complete_p_o
    after update
    on iosdb.product_order
    for each row
BEGIN

    SELECT product.prod_mat_expenditure
    INTO @mat_expended
    FROM product
    WHERE product.product_id = new.product_id;

    IF(old.completed = false && new.completed = true) THEN
        UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - (@mat_expended * new.quantity)
        WHERE new.p_m_calc_id = production_material_calculated.p_m_calc_id;
    ELSEIF(old.completed = true && new.completed = false) THEN
                UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated + (@mat_expended * new.quantity)
        WHERE new.p_m_calc_id = production_material_calculated.p_m_calc_id;
    END IF;

END;

create definer = root@localhost trigger iosdb.add_log_material_use
    after insert
    on iosdb.production_material_calculated
    for each row
BEGIN

    INSERT INTO material_usage_audit (material_usage_audit_id, delta_amount, color_id, manufacturer_id, material_type_id, date_performed) VALUES
    (null, new.amount_calculated, new.color_id, new.manufacturer_id, new.material_type_id, now());

END;

create definer = root@localhost trigger iosdb.log_material_use
    after update
    on iosdb.production_material_calculated
    for each row
BEGIN

    IF(old.amount_calculated != new.amount_calculated) THEN
        INSERT INTO material_usage_audit (material_usage_audit_id, delta_amount, color_id, manufacturer_id, material_type_id, date_performed)
                                            VALUES(
                                                null,
                                                new.amount_calculated - old.amount_calculated,
                                                new.color_id,
                                                new.manufacturer_id,
                                                new.material_type_id,
                                                now());
    END IF;

END;

create table iosdb.user
(
    user_id  int auto_increment
        primary key,
    password varchar(255) null,
    role     varchar(255) null,
    username varchar(255) null
);

