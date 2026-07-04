package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fc {
    public final ArrayList a;

    public fc(int i) {
        switch (i) {
            case 1:
                this.a = new ArrayList(32);
                return;
            default:
                this.a = new ArrayList();
                return;
        }
    }

    public void a(float f, float f2) {
        this.a.add(new yk0(f, f2));
    }

    public void b(float f, float f2, float f3, float f4) {
        this.a.add(new al0(f, f2, f3, f4));
    }

    public void c(float f, float f2) {
        this.a.add(new uk0(f, f2));
    }

    public void d(float f) {
        this.a.add(new dl0(f));
    }
}
