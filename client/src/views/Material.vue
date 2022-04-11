<template>
    <section class="material-view">
        <el-container class="material-view-container">
            <el-aside class="material-dash-aside">
                <el-container class="material-dash-container">
                    <el-header class="material-dash-header">
                        <el-row>
                            <el-col>
                                <h1 id="material-aside-label">MATERIAL</h1>
                            </el-col>
                        </el-row>
                    </el-header>

                    <el-divider id="main-divider"/>

                    <el-row id="material-actions-row">
                        <el-col>
                            <h1 style="font-size: 1.5em;">Actions</h1>
                            <div style="margin-top: 10px;">
                                <el-button type="success" plain round :icon="DocumentAdd" @click="drawer=true">Add New Material</el-button>
                                <el-divider direction="vertical"></el-divider>
                                <el-dropdown trigger="click">
                                    <el-button type="plain" plain round :icon="ArrowDown">Generate Report</el-button>
                                    <template #dropdown>
                                        <el-dropdown-menu>
                                            <el-dropdown-item @click="generateMaterialReport" :icon="Document">Material Report</el-dropdown-item>
                                            <el-dropdown-item @click="generateUsageReport" :icon="Document">Usage Report</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </template>
                                </el-dropdown>
                            </div>
                        </el-col>
                    </el-row>

                    <el-divider id="secondary-divider"/>
                </el-container>
            </el-aside>

            <el-main class="material-table-main">
                <el-tabs id="tabs" v-model="activeTab" :tab-position="'top'" type="card" stretch style="height: 100%;">
                    <el-tab-pane label="Audit" name="audit" style="height: 100%;">
                        <el-table :data="auditMaterial.filter((data) => !search || data.name.toLowerCase().includes(auditSearch.toLowerCase()) || data.manufacturer.manufacturer.includes(search))"
                                  class="material-audit-table" v-loading="loadingAuditTable"
                                  stripe height="100%">

                            <el-table-column prop="name" label="Name" sortable min-width="100"></el-table-column>
                            <el-table-column prop="manufacturer.manufacturer" label="Manufacturer" align="right"></el-table-column>
                            <el-table-column prop="color.color" label="Color" align="right"></el-table-column>
                            <el-table-column prop="materialType.type" label="Material Type" align="right"></el-table-column>
                            <el-table-column prop="amount" label="Amount (in)" sortable align="right"></el-table-column>
                            <el-table-column prop="dateInserted" label="Date Inserted" sortable :formatter="dateFormatter" align="right"></el-table-column>
                            <el-table-column label="Actions" align="right" width="130">
                                <template #default="scope">

                                    <el-tooltip effect="light" content="Edit" placement="left">
                                        <el-button :icon="Edit" type="primary" circle @click="handleMaterialEdit(scope.$index, scope.row)"></el-button>
                                    </el-tooltip>

                                    <el-popconfirm
                                        :title="'Are you sure you want to delete material: ' + scope.row.name + '?'"
                                        confirm-button-type="danger"
                                        cancel-button-type="info"
                                        cancel-button-text="No, Dont Delete"
                                        icon-color="red"
                                        @confirm.stop="handleMaterialDelete(scope.$index, scope.row)"
                                    >
                                        <template #reference>
                                            <el-button :icon="Delete" type="danger" circle></el-button>
                                        </template>
                                    </el-popconfirm>
                                </template>
                            </el-table-column>

                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="Calculated" name="calc">
                        <el-table :data="calcMaterial.filter((data) => !search || data.name.toLowerCase().includes(calcSearch.toLowerCase()) || data.manufacturer.manufacturer.includes(search))"
                                  class="material-calc-table" v-loading="loadingCalcTable"
                                  stripe height="100%">

                            <el-table-column prop="name" label="Name" sortable></el-table-column>
                            <el-table-column prop="manufacturer.manufacturer" label="Manufacturer"></el-table-column>
                            <el-table-column prop="color.color" label="Color"></el-table-column>
                            <el-table-column prop="materialType.type" label="Material Type"></el-table-column>
                            <el-table-column prop="amount" label="Total Amount" sortable></el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
            </el-main>
            <el-drawer v-model="drawer" title="Add or Edit Material" direction="ltr" :before-close="handleCloseDrawer" destroy-on-close>
                <MaterialForm/>
            </el-drawer>
        </el-container>
    </section>
</template>

<script>
import axios from "axios";
import moment from "moment";
import MaterialForm from "@/components/forms/MaterialForm";
import {ElMessageBox} from "element-plus";
import {Delete, Edit, DocumentAdd, Document, ArrowDown} from "@element-plus/icons-vue"
import {shallowRef} from "vue";


export default {
    name: "Material",
    components: {MaterialForm},

    data() {
        return {
            auditMaterial: [],
            calcMaterial: [],
            auditSearch: '',
            calcSearch: '',
            drawer: false,
            loadingAuditTable: false, loadingCalcTable: false,
            activeTab: 'audit',
            DocumentAdd: shallowRef(DocumentAdd), Document: shallowRef(Document), ArrowDown: shallowRef(ArrowDown), Edit: shallowRef(Edit), Delete: shallowRef(Delete)
        }
    },
    created() {
        this.fetchAuditMaterial()
        this.fetchCalcMaterial()
    },
    methods: {
        fetchAuditMaterial() {
            this.loadingAuditTable = true
            let apiUrl = '/production-material'

            axios.get(apiUrl).then((res) => {
                this.auditMaterial = res.data
                this.loadingAuditTable = false
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        fetchCalcMaterial() {
            this.loadingCalcTable = true
            let apiUrl = '/production-material/calculated'

            axios.get(apiUrl).then((res) => {
                this.calcMaterial = res.data
                this.loadingCalcTable = false
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong: " + error)
            })
        },
        handleMaterialEdit(index, row) {
            this.selectedMaterial = row
            this.drawer = true
        },
        handleMaterialDelete(index, row) {
            let apiUrl = "/production-material/" + row.productionMaterialId

            axios.delete(apiUrl).then(() => {
                this.auditMaterial.splice(this.auditMaterial.indexOf(row), 1)
            }).catch(error => {
                ElMessageBox.alert('Something went wrong: ' + error)
            })
        },
        handleCloseDrawer() {
            this.drawer = false
        },


        generateMaterialReport() {
            let apiUrl = "/report/calculated-material"

            axios.get(apiUrl, {responseType: 'blob'}).then((res) => {
                window.open(URL.createObjectURL(res.data))
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong" + error)
            })
        },
        generateUsageReport() {
            let apiUrl = "/report/material-usage"

            axios.get(apiUrl, {responseType: 'blob'}).then((res) => {
                window.open(URL.createObjectURL(res.data))
            }).catch(error => {
                alert(error)
            })
        },

        dateFormatter(row) {
            return moment(row).format('MM-DD-YYYY')
        }
    }
}
</script>

<style scoped>
.material-view-container {
    height: 100%;
}
.material-dash-aside {
    width: 415px;
    height: 100%;
    background-color: #545c64;
    color: white;
}
.material-dash-container {
    font-family: 'Product Sans', sans-serif;
    text-align: center;
    justify-items: center;
    align-content: center;
    justify-content: center;
}
.material-dash-header {
    display: flex;
    margin: 0;
    justify-content: center;
    align-items: center;
    text-align: center;
    vertical-align: middle;
}

.material-table-main {
    max-height: 100%;
}
.material-audit-table {
    height: 100%;
    width: 100%;
}
.material-calc-table {
    height: 100%;
    width: 100%;
}

#main-divider {
    height: 2px;
    width: 100%;
    border: none;
    color: #FFAE42;
    background-color: #FFAE42;
}
#secondary-divider {
    height: 2px;
    width: 50%;
    margin-left: 25%;
    margin-right: 25%;
    border: none;
    color: #FFAE42;
    background-color: #FFAE42;
}

#tabs {

}
#material-aside-label {
    font-size: 2em;
}
</style>