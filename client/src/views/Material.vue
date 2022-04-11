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
                        </el-col>
                    </el-row>

                    <el-divider id="secondary-divider"/>
                </el-container>
            </el-aside>

            <el-main class="material-table-main">
                <el-tabs id="tabs" v-model="activeTab" :tab-position="'top'" type="card" stretch>
                    <el-tab-pane label="Audit" name="audit">

                        <el-table :data="materialAudit.filter((data) => !search || data.name.toLowerCase().includes(search.toLowerCase()) || data.manufacturer.manufacturer.includes(search))"
                                  class="material-table" v-loading="loadingTable"
                                  stripe height="100%">

                            <el-table-column prop="name" label="Name" sortable></el-table-column>
                            <el-table-column prop="manufacturer.manufacturer" label="Manufacturer"></el-table-column>
                            <el-table-column prop="color" label="Color"></el-table-column>
                            <el-table-column prop="materialType.type" label="Material Type"></el-table-column>
                            <el-table-column prop="amount" label="Amount (in)" sortable></el-table-column>
                            <el-table-column prop="dateInserted" label="Date Inserted" sortable></el-table-column>

                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="Calculated" name="calc">CALCACALCALCALCLACLACLALCALCALCLACLALCALCALC</el-tab-pane>
                </el-tabs>
            </el-main>
            <el-drawer v-model="drawer" title="Add or Edit Material" direction="ltr" :before-close="handleCloseDrawer" destroy-on-close>

            </el-drawer>
        </el-container>
    </section>
</template>

<script>
import {DocumentAdd, Document, ArrowDown} from "@element-plus/icons-vue"
import {shallowRef} from "vue";
import axios from "axios";

export default {
    name: "Material",

    data() {
        return {
            materialAudit: [],
            materialCalc: [],
            search: '',
            drawer: false,
            loadingTable: false,
            activeTab: 'audit',
            DocumentAdd: shallowRef(DocumentAdd), Document: shallowRef(Document), ArrowDown: shallowRef(ArrowDown)
        }
    },
    methods: {
        handleCloseDrawer() {
            this.drawer = false
        },
        generateMaterialReport() {
            let apiUrl = "/report/calculated-material"

            axios.get(apiUrl, {responseType: 'blob'}).then((res) => {
                window.open(URL.createObjectURL(res.data))
            }).catch(error => {
                alert(error)
            })
        },
        generateUsageReport() {
            let apiUrl = "/report/material-usage"

            axios.get(apiUrl, {responseType: 'blob'}).then((res) => {
                window.open(URL.createObjectURL(res.data))
            }).catch(error => {
                alert(error)
            })
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
.material-table {
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