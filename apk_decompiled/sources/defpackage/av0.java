package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class av0 {
    public final String a;
    public final kv b;
    public final boolean c;

    public av0(String str, kv kvVar) {
        this.a = str;
        this.b = kvVar;
    }

    public final String toString() {
        return "AccessibilityKey: " + this.a;
    }

    public /* synthetic */ av0(String str) {
        this(str, yu0.h);
    }

    public av0(String str, int i) {
        this(str);
        this.c = true;
    }

    public av0(String str, boolean z, kv kvVar) {
        this(str, kvVar);
        this.c = z;
    }
}
