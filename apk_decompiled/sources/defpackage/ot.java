package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ot {
    public static final ot e;
    public static final ot f;
    public static final ot g;
    public static final /* synthetic */ ot[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, ot] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, ot] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Enum, ot] */
    static {
        ?? r0 = new Enum("Active", 0);
        e = r0;
        ?? r1 = new Enum("ActiveParent", 1);
        f = r1;
        Enum r3 = new Enum("Captured", 2);
        ?? r5 = new Enum("Inactive", 3);
        g = r5;
        h = new ot[]{r0, r1, r3, r5};
    }

    public static ot valueOf(String str) {
        return (ot) Enum.valueOf(ot.class, str);
    }

    public static ot[] values() {
        return (ot[]) h.clone();
    }

    public final boolean a() {
        int ordinal = ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal != 3) {
                        v7.k();
                        return false;
                    }
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
