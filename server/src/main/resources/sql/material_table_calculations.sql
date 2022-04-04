USE iosdb;

# Trigger for insertions #
CREATE OR REPLACE TRIGGER add_calculate_material_amount
    AFTER INSERT ON iosdb.production_material_audit
    FOR EACH ROW
BEGIN

    IF EXISTS
        (SELECT * FROM production_material_calculated
                WHERE new.manufacturer = production_material_calculated.manufacturer
                AND new.color_id = production_material_calculated.color_id
                AND new.material_type_id = production_material_calculated.material_type_id
                AND new.name = production_material_calculated.name )
    THEN
        UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = new.amount + production_material_calculated.amount_calculated
        WHERE new.manufacturer = production_material_calculated.manufacturer
        AND new.color_id = production_material_calculated.color_id
        AND new.material_type_id = production_material_calculated.material_type_id
        AND new.name = production_material_calculated.name;
    ELSE
        INSERT INTO production_material_calculated VALUES(null, new.amount, new.manufacturer, new.name, new.color_id, new.material_type_id);
    END IF;

END;

# Trigger for updates #
CREATE OR REPLACE TRIGGER update_calculate_material_amount
    AFTER UPDATE ON iosdb.production_material_audit
    FOR EACH ROW

BEGIN
    IF(new.color_id != old.color_id
        OR new.material_type_id != old.material_type_id
        OR new.manufacturer != old.manufacturer
        OR new.name != old.name)
    THEN
        UPDATE production_material_calculated
        SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - old.amount
        WHERE old.manufacturer = production_material_calculated.manufacturer
        AND old.color_id = production_material_calculated.color_id
        AND old.material_type_id = production_material_calculated.material_type_id
        AND old.name = production_material_calculated.name;

        IF(EXISTS(SELECT * FROM production_material_calculated
                WHERE new.manufacturer = production_material_calculated.manufacturer
                AND new.color_id = production_material_calculated.color_id
                AND new.material_type_id = production_material_calculated.material_type_id
                AND new.name = production_material_calculated.name))
        THEN
            UPDATE production_material_calculated
            SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated + new.amount
            WHERE new.manufacturer = production_material_calculated.manufacturer
            AND new.color_id = production_material_calculated.color_id
            AND new.material_type_id = production_material_calculated.material_type_id
            AND new.name = production_material_calculated.name;
        ELSE
            INSERT INTO production_material_calculated VALUES(null, new.amount, new.manufacturer, new.name, new.color_id, new.material_type_id);
        END IF;
    ELSE
        IF(new.amount > old.amount) THEN
            UPDATE production_material_calculated
            SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated + (new.amount - old.amount)
            WHERE new.manufacturer = production_material_calculated.manufacturer
            AND new.color_id = production_material_calculated.color_id
            AND new.material_type_id = production_material_calculated.material_type_id
            AND new.name = production_material_calculated.name;
        ELSEIF(new.amount < old.amount) THEN
            UPDATE production_material_calculated
            SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - (old.amount - new.amount)
            WHERE new.manufacturer = production_material_calculated.manufacturer
            AND new.color_id = production_material_calculated.color_id
            AND new.material_type_id = production_material_calculated.material_type_id
            AND new.name = production_material_calculated.name;
        END IF;
    END IF;
END;


# Trigger for deletions #
CREATE OR REPLACE TRIGGER delete_calculate_material_amount
    AFTER DELETE ON iosdb.production_material_audit
    FOR EACH ROW
BEGIN

    UPDATE production_material_calculated
    SET production_material_calculated.amount_calculated = production_material_calculated.amount_calculated - old.amount
    WHERE old.manufacturer = production_material_calculated.manufacturer
    AND old.color_id = production_material_calculated.color_id
    AND old.material_type_id = production_material_calculated.material_type_id
    AND old.name = production_material_calculated.name;

END