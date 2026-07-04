package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mo0 {
    public yh a;
    public int b;
    public wv c;
    public kv d;
    public int e;
    public oe0 f;
    public ve0 g;

    public mo0(yh yhVar) {
        this.a = yhVar;
    }

    public final boolean a() {
        boolean z;
        if (this.a != null) {
            wv wvVar = this.c;
            if (wvVar != null) {
                z = wvVar.a();
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final w20 b(Object obj) {
        w20 s;
        yh yhVar = this.a;
        if (yhVar != null && (s = yhVar.s(this, obj)) != null) {
            return s;
        }
        return w20.e;
    }

    public final void c() {
        yh yhVar = this.a;
        if (yhVar != null) {
            yhVar.s = true;
            yhVar.x.g();
        }
        this.a = null;
        this.f = null;
        this.g = null;
        this.d = null;
    }

    public final void d(boolean z) {
        int i;
        int i2 = this.b;
        if (z) {
            i = i2 | 32;
        } else {
            i = i2 & (-33);
        }
        this.b = i;
    }
}
