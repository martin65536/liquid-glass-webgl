package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wm0 {
    public final long a;
    public final long b;
    public final long c;
    public final long d;
    public final boolean e;
    public final float f;
    public final int g;
    public final boolean h;
    public final ArrayList i;
    public final long j;
    public final float k;
    public final long l;
    public final long m;

    public wm0(long j, long j2, long j3, long j4, boolean z, float f, int i, boolean z2, ArrayList arrayList, long j5, float f2, long j6, long j7) {
        this.a = j;
        this.b = j2;
        this.c = j3;
        this.d = j4;
        this.e = z;
        this.f = f;
        this.g = i;
        this.h = z2;
        this.i = arrayList;
        this.j = j5;
        this.k = f2;
        this.l = j6;
        this.m = j7;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof wm0) {
                wm0 wm0Var = (wm0) obj;
                if (n30.s(this.a, wm0Var.a) && this.b == wm0Var.b && ch0.c(this.c, wm0Var.c) && ch0.c(this.d, wm0Var.d) && this.e == wm0Var.e && Float.compare(this.f, wm0Var.f) == 0 && this.g == wm0Var.g && this.h == wm0Var.h && this.i.equals(wm0Var.i) && ch0.c(this.j, wm0Var.j) && Float.compare(this.k, wm0Var.k) == 0 && ch0.c(this.l, wm0Var.l) && ch0.c(this.m, wm0Var.m)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        long j = this.a;
        long j2 = this.b;
        int e = (ch0.e(this.d) + ((ch0.e(this.c) + (((((int) (j ^ (j >>> 32))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31)) * 31)) * 31;
        int i2 = 1237;
        if (this.e) {
            i = 1231;
        } else {
            i = 1237;
        }
        int s = (d3.s(this.f, (e + i) * 31, 31) + this.g) * 31;
        if (this.h) {
            i2 = 1231;
        }
        return ch0.e(this.m) + ((ch0.e(this.l) + d3.s(this.k, (ch0.e(this.j) + ((this.i.hashCode() + ((s + i2) * 31)) * 31)) * 31, 31)) * 31);
    }

    public final String toString() {
        return "PointerInputEventData(id=" + ((Object) n30.I(this.a)) + ", uptime=" + this.b + ", positionOnScreen=" + ((Object) ch0.i(this.c)) + ", position=" + ((Object) ch0.i(this.d)) + ", down=" + this.e + ", pressure=" + this.f + ", type=" + ((Object) an0.a(this.g)) + ", activeHover=" + this.h + ", historical=" + this.i + ", scrollDelta=" + ((Object) ch0.i(this.j)) + ", scaleGestureFactor=" + this.k + ", panGestureOffset=" + ((Object) ch0.i(this.l)) + ", originalEventPosition=" + ((Object) ch0.i(this.m)) + ')';
    }
}
