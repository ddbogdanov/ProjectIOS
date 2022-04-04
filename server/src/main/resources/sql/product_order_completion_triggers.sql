USE iosdb;

CREATE OR REPLACE TRIGGER deduct_material_after_complete_p_o
    AFTER UPDATE ON product_order
    FOR EACH ROW

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
