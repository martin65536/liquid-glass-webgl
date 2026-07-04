package defpackage;

import android.os.Trace;
import android.view.Choreographer;
import android.view.View;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d6 implements in0, View.OnAttachStateChangeListener, Runnable, Choreographer.FrameCallback {
    public static long l;
    public final View e;
    public boolean g;
    public boolean j;
    public long k;
    public final PriorityQueue f = new PriorityQueue(11, new b6(0));
    public final Choreographer h = Choreographer.getInstance();
    public final c6 i = new Object();

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003d, code lost:
    
        if (r0 >= 30.0f) goto L11;
     */
    /* JADX WARN: Type inference failed for: r0v2, types: [c6, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public d6(android.view.View r5) {
        /*
            r4 = this;
            r4.<init>()
            r4.e = r5
            java.util.PriorityQueue r0 = new java.util.PriorityQueue
            b6 r1 = new b6
            r2 = 0
            r1.<init>(r2)
            r2 = 11
            r0.<init>(r2, r1)
            r4.f = r0
            android.view.Choreographer r0 = android.view.Choreographer.getInstance()
            r4.h = r0
            c6 r0 = new c6
            r0.<init>()
            r4.i = r0
            long r0 = defpackage.d6.l
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L49
            android.view.Display r0 = r5.getDisplay()
            boolean r1 = r5.isInEditMode()
            if (r1 != 0) goto L40
            if (r0 == 0) goto L40
            float r0 = r0.getRefreshRate()
            r1 = 1106247680(0x41f00000, float:30.0)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 < 0) goto L40
            goto L42
        L40:
            r0 = 1114636288(0x42700000, float:60.0)
        L42:
            r1 = 1315859240(0x4e6e6b28, float:1.0E9)
            float r1 = r1 / r0
            long r0 = (long) r1
            defpackage.d6.l = r0
        L49:
            r5.addOnAttachStateChangeListener(r4)
            boolean r5 = r5.isAttachedToWindow()
            if (r5 == 0) goto L55
            r5 = 1
            r4.j = r5
        L55:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d6.<init>(android.view.View):void");
    }

    @Override // defpackage.in0
    public final void a(hn0 hn0Var) {
        this.f.add(new rn0(1, hn0Var));
        if (!this.g) {
            this.g = true;
            this.e.post(this);
        }
    }

    public final boolean b() {
        c6 c6Var = this.i;
        long a = c6Var.a();
        f31.V("compose:lazy:prefetch:available_time_nanos", a);
        boolean z = true;
        if (a > 0) {
            PriorityQueue priorityQueue = this.f;
            Object peek = priorityQueue.peek();
            peek.getClass();
            if (!((rn0) peek).b.c(c6Var)) {
                priorityQueue.poll();
                z = false;
            }
            c6Var.a = false;
        }
        return z;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        if (this.j) {
            this.k = j;
            this.e.post(this);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        this.j = true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.j = false;
        this.e.removeCallbacks(this);
        this.h.removeFrameCallback(this);
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        PriorityQueue priorityQueue = this.f;
        if (!priorityQueue.isEmpty() && this.g && this.j) {
            View view = this.e;
            if (view.getWindowVisibility() == 0) {
                long nanos = TimeUnit.MILLISECONDS.toNanos(view.getDrawingTime());
                if (System.nanoTime() > (2 * l) + nanos) {
                    z = true;
                } else {
                    z = false;
                }
                c6 c6Var = this.i;
                c6Var.a = z;
                c6Var.b = Math.max(this.k, nanos) + l;
                boolean z2 = false;
                while (!priorityQueue.isEmpty() && !z2) {
                    if (c6Var.a) {
                        Trace.beginSection("compose:lazy:prefetch:idle_frame");
                        try {
                            z2 = b();
                        } finally {
                            Trace.endSection();
                        }
                    } else {
                        z2 = b();
                    }
                }
                if (z2) {
                    this.h.postFrameCallback(this);
                } else {
                    this.g = false;
                }
                f31.V("compose:lazy:prefetch:available_time_nanos", 0L);
                return;
            }
        }
        this.g = false;
    }
}
