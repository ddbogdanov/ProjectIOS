<template>
    <section class="material-view">
        <el-container class="material-view-container">
            <el-aside class="material-dash-aside">
                <el-container class="material-dash-container">
                    <el-divider id="main-divider"/>
                    <el-header class="material-dash-header">
                        <el-row style="width: 100%;">
                            <el-col>
                                <h1 id="material-aside-label">MATERIAL</h1>
                            </el-col>
                            <el-divider class="secondary-divider" style="margin-top: 10px; margin-bottom: 10px;"/>
                        </el-row>
                    </el-header>

                    <el-row id="material-actions-row">
                        <el-col style="margin-top: 20px">
                            <h1 style="font-size: 1.5em;">Actions</h1>
                            <div style="margin-top: 10px;">
                                <el-button type="success" plain round :icon="DocumentAdd" @click="drawer=true">Add New Material</el-button>
                                <el-divider direction="vertical" style="background-color: #FFAE42"></el-divider>
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

                    <el-row id="materials-total-row" style="margin-top: auto; margin-bottom: auto">
                        <el-divider class="secondary-divider"/>
                        <el-col>
                            <h1 id="materials-total-label">Total Unique Materials:</h1>
                            <label id="materials-count-label">{{calcMaterial.length}}</label>
                        </el-col>
                        <el-divider class="secondary-divider"/>
                    </el-row>

                    <el-row style="margin: auto 20px 20px;">
                        <el-col>
                            <el-card>
                                <apexchart
                                    type="line"
                                    :options="chartOptions"
                                    :series="series"
                                ></apexchart>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-container>
            </el-aside>

            <el-main class="material-table-main">
                <el-tabs id="tabs" v-model="activeTab" :tab-position="'top'" type="border-card" stretch style="display:flex; flex-direction: column; height: 100%;">
                    <el-tab-pane label="Audit" name="audit" style="height: 100%; flex-grow: 1;">
                        <el-table :data="auditMaterial.filter((data) => !search || data.name.toLowerCase().includes(search.toLowerCase()) || data.manufacturer.manufacturer.includes(search))"
                                  class="material-audit-table" v-loading="loadingAuditTable"
                                  stripe height="100vh" style="max-height: 100%;">

                            <el-table-column>
                                <template #header>
                                    <el-row justify="space-around" style="align-items: center">
                                        <el-col :span="5" >
                                            <el-button :icon="Refresh" circle plain size="small" @click.stop="fetchAuditMaterial"></el-button>
                                            <label style="margin-left:10px; font-family: 'Product Sans', sans-serif">Materials - Audit</label>
                                        </el-col>
                                        <el-col :span="5" style="margin-left:auto;">
                                            <el-input v-model="search" placeholder="Search by name or manufacturer" :suffix-icon="Search"/>
                                        </el-col>
                                    </el-row>
                                 </template>

                                <el-table-column prop="name" label="Name" sortable min-width="200"></el-table-column>
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
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <el-tab-pane label="Calculated" name="calc" style="height: 100%; flex-grow: 1;">
                        <el-table :data="calcMaterial.filter((data) => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                                  class="material-calc-table" v-loading="loadingCalcTable"
                                  stripe height="100vh" style="max-height: 100%;">

                            <el-table-column>
                                <template #header>
                                    <el-row justify="space-around">
                                        <el-col :span="5" >
                                            <el-button :icon="Refresh" circle plain size="small" @click.stop="fetchCalcMaterial"></el-button>
                                            <label style="margin-left:10px; font-family: 'Product Sans', sans-serif">Materials - Calculated</label>
                                        </el-col>
                                        <el-col :span="5" style="margin-left:auto;">
                                            <el-input v-model="search" placeholder="Search by name or manufacturer" :suffix-icon="Search"/>
                                        </el-col>
                                    </el-row>
                                </template>

                                <el-table-column prop="name" label="Name" min-width="200" sortable></el-table-column>
                                <el-table-column prop="manufacturer.manufacturer" label="Manufacturer" align="right"></el-table-column>
                                <el-table-column prop="color.color" label="Color" align="right"></el-table-column>
                                <el-table-column prop="materialType.type" label="Material Type" align="right"></el-table-column>
                                <el-table-column prop="amount" label="Total Amount" align="right" sortable></el-table-column>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                </el-tabs>
            </el-main>
            <el-drawer v-model="drawer" title="Add or Edit Material" direction="ltr" :before-close="handleCloseDrawer" destroy-on-close>
                <MaterialForm :materialProp="selectedMaterial"/>
            </el-drawer>
        </el-container>
    </section>
</template>

<script>
import axios from "axios";
import moment from "moment";
import MaterialForm from "@/components/forms/MaterialForm";
import {ElMessageBox} from "element-plus";
import {Delete, Edit, DocumentAdd, Document, ArrowDown, Refresh, Search} from "@element-plus/icons-vue"
import {shallowRef} from "vue";


export default {
    name: "Material",
    components: {MaterialForm},

    data() {
        return {
            auditMaterial: [ ], calcMaterial: [ ],
            search: '',
            drawer: false,
            activeTab: 'audit',
            loadingAuditTable: false, loadingCalcTable: false,
            DocumentAdd: shallowRef(DocumentAdd), Document: shallowRef(Document), ArrowDown: shallowRef(ArrowDown), Edit: shallowRef(Edit), Delete: shallowRef(Delete), Refresh: shallowRef(Refresh), Search: shallowRef(Search),
            selectedMaterial: {name: '', amount: '', color: {colorId: '', color: ''}, manufacturer: {manufacturerId: '', manufacturer: ''}, materialType: {materialTypeId: '', type: ''}},

            chartOptions: {
                chart: {
                    id: "vuechart",
                    toolbar: {
                        tools: {
                            download: true,
                            selection: false,
                            zoom: false,
                            zoomin: false,
                            zoomout: false,
                            pan: false,
                        }
                    },
                },
                title: {
                    text: "Material Additions and Deductions",
                    style: {
                        fontFamily: 'Product Sans'
                    },
                },
                subtitle: {
                    text: "In the past week",
                    style: {
                        fontFamily: 'Product Sans'
                    },
                },
                xaxis: {
                    categories: [],
                    labels: {
                        show: false
                    },
                    show: false, axisBorder: { show: false, }
                },
                yaxis: {
                    labels: {
                        show: false
                    },
                },
                dataLabels: {
                    enabled: false
                },
                legend: {
                    show: false
                },
                theme: {
                    mode: 'light',
                    palette: 'palette1'
                }
            },
            series: [
                {
                    name: "amount",
                    data: [],
                },
            ],
        }
    },
    mounted() {
        this.$bus.on('closeMaterialForm', () => {
            this.drawer = false
            this.fetchAuditMaterial()
        })
    },
    created() {
        this.fetchAuditMaterial()
        this.fetchCalcMaterial()
        this.fetchChartData()
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
            ElMessageBox.confirm('Are you sure want to close the material form?')
                .then(() => {
                    this.selectedMaterial = {name: '', amount: '', color: {colorId: '', color: ''}, manufacturer: {manufacturerId: '', manufacturer: ''}, materialType: {materialTypeId: '', type: ''}}
                    this.drawer = false
                    this.fetchAuditMaterial()
                    this.fetchCalcMaterial()
                })
        },

        generateMaterialReport() {
            let apiUrl = "/report/calculated-material"

            axios.get(apiUrl, {responseType: 'blob'}).then((res) => {
                window.open(URL.createObjectURL(res.data))
            }).catch((error) => {
                ElMessageBox.alert("Something went wrong " + error)
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
        fetchChartData() {
            let apiUrl = "/material-usage-audit/week"

            axios.get(apiUrl).then((res) => {
                let resData = res.data

                for(let i=0; i<resData.length; i++) {
                    this.chartOptions.xaxis.categories.push(resData[i].datePerformed)
                    this.series[0].data.push(resData[i].deltaAmount)
                }

            }).catch(error => {
                ElMessageBox.alert("Something went wrong " + error)
            })
        },

        dateFormatter(row) {
            return moment(row.dateInserted).format('MM-DD-YYYY')
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
    background-color: #33333D;
    color: white;
}
.material-dash-container {
    font-family: 'Product Sans', sans-serif;
    text-align: center;
    align-content: center;
    height: 100%;
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
    height: 100%;
}
.material-audit-table {
    height: 100%;
    width: 100%;
}
.material-calc-table {
    min-height: 100%;
    width: 100%;
}

#main-divider {
    height: 2px;
    width: 100%;
    border: none;
    background-color: #84898c;
    margin-top: 0;
}
.secondary-divider {
    height: 2px;
    width: 60%;
    margin-left: 20%;
    margin-right: 20%;
    border: none;
    color: #FFAE42;
    background-color: #FFAE42;
}

#material-actions-row {

}
#material-aside-label {
    font-size: 2em;
}
#materials-aside-label {
    font-size: 2em;
}
#materials-total-label {
    font-size: 1.5em;
}
#materials-count-label {
    font-size: 6em;
}
</style>