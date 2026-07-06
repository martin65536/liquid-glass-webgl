package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ik {
    public static final ik e;
    public static final ik f;
    public static final ik g;
    public static final /* synthetic */ ik[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [ik, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r1v1, types: [ik, java.lang.Enum] */
    /* JADX WARN: Type inference failed for: r3v1, types: [ik, java.lang.Enum] */
    static {
        ?? r0 = new Enum("COROUTINE_SUSPENDED", 0);
        e = r0;
        ?? r1 = new Enum("UNDECIDED", 1);
        f = r1;
        ?? r3 = new Enum("RESUMED", 2);
        g = r3;
        h = new ik[]{r0, r1, r3};
    }

    public static ik valueOf(String str) {
        return (ik) Enum.valueOf(ik.class, str);
    }

    public static ik[] values() {
        return (ik[]) h.clone();
    }
}
