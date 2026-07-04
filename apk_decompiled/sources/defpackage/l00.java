package defpackage;

import android.view.GestureDetector;
import android.view.MotionEvent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class l00 implements GestureDetector.OnGestureListener {
    public final /* synthetic */ m00 a;

    public l00(m00 m00Var) {
        this.a = m00Var;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        m00 m00Var = this.a;
        x3 x3Var = m00Var.a;
        if (!m00Var.c) {
            int i = m00Var.b;
            int i2 = 2;
            if (i == 1) {
                if (Math.abs(f) > Math.abs(f2)) {
                    if (f > 0.0f) {
                        i2 = 1;
                    }
                    x3Var.e(new bt(i2));
                    return true;
                }
            } else if (i == 2 && Math.abs(f2) > Math.abs(f)) {
                if (f2 > 0.0f) {
                    i2 = 1;
                }
                x3Var.e(new bt(i2));
            }
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final void onLongPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final void onShowPress(MotionEvent motionEvent) {
    }
}
