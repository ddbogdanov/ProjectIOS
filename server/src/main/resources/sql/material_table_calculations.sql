CREATE OR REPLACE TRIGGER add_calculate_material_amount
    AFTER INSERT ON iosdb.production_material_audit
    FOR EACH ROW
BEGIN

    UPDATE production_material_calculated
    SET production_material_calculated.amount_calculated = new.amount + production_material_calculated.amount_calculated
    WHERE new.manufacturer = production_material_calculated.manufacturer
      AND new.color_id = production_material_calculated.color_id
      AND new.material_type_id = production_material_calculated.material_type_id
      AND new.name = production_material_calculated.name;

END


# TODO Implement
# CREATE OR REPLACE TRIGGER update_calculate_material_amount
#     AFTER UPDATE ON iosdb.production_material_audit
#     FOR EACH ROW
# BEGIN
#
# END
#
#
#
#
#
# CREATE OR REPLACE TRIGGER delete_calculate_material_amount
#     AFTER INSERT ON iosdb.production_material_audit
#     FOR EACH ROW
#
# END