package code.accessoory;

/**
 * Created by Алексей on 22.04.2017.
 */
public enum MenuType {
    CUSTOMERS(new String[]{"directories", "Customers.fxml"}),
    RAW_MATERIALS(new String[]{"directories", "RawMaterial.fxml"}),
    MATERIAL_TYPE(new String[]{"directories", "MaterialType.fxml"}),
    MATERIALS(new String[]{"directories", "Materials.fxml"}),
    CONTRACTS(new String[]{"directories", "Contracts.fxml"}),
    PRODUCTIONS(new String[]{"directories","Productions.fxml"}),
    UNITS(new String[]{"directories", "Units.fxml"}),
    CONTRACT_INPUT(new String[]{"directories","input_form","ContractInput.fxml"}),
    COSTOMER_INPUT(new String[]{"directories","input_form","CustomerInput.fxml"}),
    MATERIAL_INPUT(new String[]{"directories","input_form","MaterialsInput.fxml"}),
    MATERIAL_TYPE_INPUT(new String[]{"directories","input_form","MaterialTypeInput.fxml"}),
    RAW_MATERIAL_INPUT(new String[]{"directories","input_form","RawMaterialInput.fxml"}),
    UNITS_INPUT(new String[]{"directories","input_form","UnitsInput.fxml"}),
    PRODUCTION_INPUT(new String[]{"directories","input_form","ProductionInput.fxml"});


    private String[] filePath;

    MenuType(String[] filePath){
        this.filePath = filePath;
    }

    public String[] getFilePath() {
        return filePath;
    }
}
