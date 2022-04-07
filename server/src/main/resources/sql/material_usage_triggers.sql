CREATE OR REPLACE TRIGGER log_material_use
    AFTER UPDATE ON iosdb.production_material_calculated
    FOR EACH ROW

BEGIN

    IF(old.amount_calculated != new.amount_calculated) THEN
        INSERT INTO material_usage_audit VALUES(
                                                null,
                                                new.amount_calculated - old.amount_calculated,
                                                new.color_id,
                                                new.manufacturer_id,
                                                new.material_type_id,
                                                now());
    END IF;

END;