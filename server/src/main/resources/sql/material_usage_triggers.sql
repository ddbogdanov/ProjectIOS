CREATE OR REPLACE TRIGGER log_material_use
    AFTER UPDATE ON iosdb.production_material_calculated
    FOR EACH ROW

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

CREATE OR REPLACE TRIGGER add_log_material_use
    AFTER INSERT ON iosdb.production_material_calculated
    FOR EACH ROW

BEGIN

    INSERT INTO material_usage_audit (material_usage_audit_id, delta_amount, color_id, manufacturer_id, material_type_id, date_performed) VALUES
    (null, new.amount_calculated, new.color_id, new.manufacturer_id, new.material_type_id, now());

END;