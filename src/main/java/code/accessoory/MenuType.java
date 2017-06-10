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
    SPECIFICATIONS(new String[]{"directories", "Specifications.fxml"}),
    PRODUCTION(new String[]{"directories", "Production.fxml"}),
    UNITS(new String[]{"directories", "Units.fxml"})
    ;


    private String[] filePath;

    MenuType(String[] filePath){
        this.filePath = filePath;
    }

    public String[] getFilePath() {
        return filePath;
    }
}
