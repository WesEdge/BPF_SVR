(function() {

    BPF.Spinny = new function(){

        this.killed = false;
        this.showing = false;
        this.timer = null;
        this.count = null;
        this.countExists = false;

        this.show = function($element, count){

            this.countExists = count != null && count > 0;
            this.count = count;
            this.$spinny = $element;
            this.$spinny.show();
            this.showing = true;
            this.killed = false;
            this.onStopCallback = null;
            this.rotateOnce();

        }

        this.rotateOnce = function(angle){

            var abs = 720;
            angle = (angle == abs) ? -abs : abs;

            this.$spinny.rotate({
                angle: 0,
                animateTo:angle,
                duration:1100,
                easing: $.easing.easeInOutExpo,
                callback: function(){

                    if ((BPF.Spinny.countExists && ((BPF.Spinny.count-=1) < 1))){
                        BPF.Spinny.kill();
                        BPF.Spinny.hide();
                        if (BPF.Spinny.onStopCallback){
                            BPF.Spinny.onStopCallback();
                        }
                    }
                    else if (BPF.Spinny.showing){

                        BPF.Spinny.killTimer();
                        BPF.Spinny.timer = $.timer(function() {
                            if (BPF.Spinny.killed){
                                BPF.Spinny.hide();
                                if (BPF.Spinny.onStopCallback){
                                    BPF.Spinny.onStopCallback();
                                }
                            }
                            else{
                                BPF.Spinny.rotateOnce(angle);
                            }

                        }).once(300);

                    }

                }
            })
        }

        this.kill = function(onStopCallback){
            this.onStopCallback = onStopCallback;
            this.killed = true;
            this.countExists = false;
            this.count = 0;
        }

        this.hide = function(){
            this.showing = false;
            this.$spinny.hide();
            this.killTimer();
        }

        this.killTimer = function(){
            if (BPF.Spinny.timer) {
                BPF.Spinny.timer.stop();
                BPF.Spinny.timer = null;
            }
        }

    }


}());



