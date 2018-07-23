<template>
    <transition name="slide-fade">
        <div class="dialog" v-show="showMask">
            <div class="dialog-container">
                <div class="dialog-title">{{title}}</div>
                <div class="content">
                    <slot></slot>
                </div>
                <div class="btns">
                    <div v-if="type != 'cancel'" class="default-btn" @click="closeBtn">
                        {{cancelText}}
                    </div>
                    <div v-if="type == 'danger'" class="danger-btn" @click="dangerBtn">
                        {{dangerText}}
                    </div>
                    <div v-if="type == 'confirm'" class="confirm-btn" @click="confirmBtn">
                        {{confirmText}}
                    </div>
                </div>
                <div class="close-btn" @click="closeMask"><i class="icon-close"></i></div>
            </div>
        </div>
    </transition>
</template>
<script>
export default {
  name: 'dialogBar',
    props: {
        value: {},
        // 类型包括 defalut 默认， danger 危险， confirm 确认，
        type:{
            type: String,
            default: 'default'
        },
        content: {
            type: String,
            default: ''
        },
        title: {
            type: String,
            default: ''
        },
        cancelText: {
            type: String,
            default: '取消'
        },
        dangerText: {
            type: String,
            default: '删除'
        },
        confirmText: {
            type: String,
            default: '确认'
        },
        backcall:{
            type: String,
            default: ''
        }
    },
    data(){
        return{
            showMask: false,
        }
    },
    methods:{
        closeMask(){
            this.showMask = false;
        },
        closeBtn(){
            this.$emit('cancel');
            this.closeMask();
        },
        dangerBtn(){
            this.$emit('danger');
            this.closeMask();
        },
        confirmBtn(){
            this.$emit('confirm');
            this.closeMask();
        }
    },
    mounted(){
        this.showMask = this.value;
    },
    watch:{
        value(newVal, oldVal){
            this.showMask = newVal;
        },
        showMask(val) {
            this.$emit('input', val);
        }
    },
}
</script>
<style lang="less" scoped>
    .dialog{
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0, 0, 0, 0.6);
        z-index: 9900;
        .dialog-container{
            width: 80%;
            height: 95%;
            background: #ffffff;
            position: absolute;
            top: 10px;
            left: 50%;
            transform: translate(-50%, 10px);
            border-radius: 8px;
            position: relative;
            .dialog-title{
                width: 100%;
                height: 60px;
                font-size: 18px;
                color: #696969;
                font-weight: 600;
                padding: 16px 50px 0 20px;
                box-sizing: border-box;
            }
            .content{
                color: #797979;
                line-height: 26px;
                padding: 0 20px;
                box-sizing: border-box;
                height: calc(100% - 140px);
                text-align: center;
            }
            .inp{
                margin: 10px 0 0 20px;
                width: 200px;
                height: 40px;
                padding-left: 4px;
                border-radius: 4px;
                border: none;
                background: #efefef;
                outline: none;
                &:focus{
                    border: 1px solid #509EE3;
                }
            }
            .btns{
                height: 60px;
                position: absolute;
                bottom: 0;
                right: 0;
                text-align: right;
                padding: 0 16px;
                box-sizing: border-box;
                & > div{
                    display: inline-block;
                    height: 40px;
                    line-height: 40px;
                    padding: 0 14px;
                    color: #ffffff;
                    background: #f1f1f1;
                    border-radius: 8px;
                    margin-right: 12px;
                    cursor: pointer;
                }
                .default-btn{
                    color: #787878;
                    &:hover{
                        color: #509EE3; 
                    }
                }
                .danger-btn{
                    background: #EF8C8C;
                    &:hover{
                        background: rgb(224, 135, 135);
                    }
                    &:active{
                        background: #EF8C8C;
                    }
                }
                .confirm-btn{
                    color: #ffffff;
                    background: #509EE3;
                    &:hover{
                        background: #6FB0EB;
                    }
                }
            }
            .close-btn{
                position: absolute;
                top: 16px;
                right: 16px;
                width: 30px;
                height: 30px;
                line-height: 30px;
                text-align: center;
                font-size: 18px;
                cursor: pointer;
                &:hover{
                    font-weight: 600;
                }
            }
        }
    }
.slide-fade-enter-active {
  transition: all .5s ease;
}
.slide-fade-leave-active {
  transition: all .5s ease;
}
.slide-fade-enter, .slide-fade-leave-to {
  transform: translateY(-20px);
  opacity: 0;
}
.icon-close{
    width: 20px;
    height: 20px;
    position: absolute;
    top: 5px;
    right: 5px;
    cursor: pointer;
}
.icon-close:before,.icon-close:after{
    position: absolute;
    content: "";
    top:50%;
    left: 0;
    background: #000;
    width: 100%;
    height: 3px;
    transition-duration:0.5s;
    -moz-transition-duration:0.5s;
    /* Safari and Chrome */
    -webkit-transition-duration:0.5s;
    /* Opera */
    -o-transition-duration:0.5s;
}
.icon-close:before{
    transform:rotate(45deg);
    -webkit-transform:rotate(45deg);
    -moz-transform:rotate(45deg);
    -ms-transform:rotate(45deg);
    -o-transform:rotate(45deg);
}
.icon-close:after{
    transform:rotate(-45deg);
    -webkit-transform:rotate(-45deg);
    -moz-transform:rotate(-45deg);
    -ms-transform:rotate(-45deg);
    -o-transform:rotate(-45deg);
}
.icon-close:hover:before{
    transform:rotate(225deg);
    -webkit-transform:rotate(225deg);
    -moz-transform:rotate(225deg);
    -ms-transform:rotate(225deg);
    -o-transform:rotate(225deg);
    background: #04649e;
}
.icon-close:hover:after{
    transform:rotate(135deg);
    -webkit-transform:rotate(135deg);
    -moz-transform:rotate(135deg);
    -ms-transform:rotate(135deg);
    -o-transform:rotate(135deg);
    background: #04649e;
}
</style>